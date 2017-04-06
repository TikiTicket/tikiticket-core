package com.tikiticket.core.api.impl;

import com.tikiticket.core.Connector;
import com.tikiticket.core.Context;
import com.tikiticket.core.PageParser;
import com.tikiticket.core.api.Profile;
import com.tikiticket.core.api.ProfileManager;
import com.tikiticket.core.base.BaseManager;
import com.tikiticket.core.exception.TikiTicketException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by veinhorn on 1.4.17.
 */
public class ProfileManagerImpl extends BaseManager implements ProfileManager {
    private static final String PROFILE_DATA_URL = "https://poezd.rw.by/wps/myportal/home/rp/private/private1";

    public ProfileManagerImpl(Connector connector) {
        super(connector);
    }

    @Override
    public Profile getProfile() throws TikiTicketException {
        Context context = connector.doGet(PROFILE_DATA_URL);
        return new ProfilePageParser().parse(context);
    }

    private class ProfilePageParser implements PageParser<Profile> {
        @Override
        public Profile parse(Context context) throws TikiTicketException {
            try {
                return parse(Jsoup.parse(context.getHtml()));
            } catch (Exception e) {
                throw new TikiTicketException("Cannot parse profile page", e);
            }
        }

        private Profile parse(Document document) {
            Element userData = document.getElementById("userReg");
            Elements trElms = userData.getElementsByTag("tr");

            ProfileImpl profile = new ProfileImpl();
            profile.setLogin(trElms.get(2).getElementsByTag("input").attr("value"));
            profile.setFirstName(trElms.get(9).getElementsByTag("input").attr("value"));
            profile.setSecondName(trElms.get(7).getElementsByTag("input").attr("value"));
            profile.setPatronymic(trElms.get(11).getElementsByTag("input").attr("value"));
            profile.setPhoneNumber(trElms.get(13).getElementsByTag("input").attr("value"));
            profile.setCountry(trElms.get(15).getElementsByTag("input").attr("value"));
            profile.setAddress(trElms.get(16).getElementsByTag("textarea").text());
            profile.setEmail(trElms.get(17).getElementsByTag("input").attr("value"));
            profile.setGender(trElms.get(19).getElementsByAttributeValue("selected", "selected").text());
            profile.setAge(trElms.get(21).getElementsByAttributeValue("selected", "selected").text());
            return profile;
        }
    }
}
