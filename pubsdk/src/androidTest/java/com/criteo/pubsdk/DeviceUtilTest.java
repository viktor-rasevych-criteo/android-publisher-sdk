package com.criteo.pubsdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.criteo.pubsdk.Util.DeviceUtil;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class DeviceUtilTest {
    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getContext();
    }

    public void createDfpCompatibleDisplayUrlTest() {
        String displayUrl = "https://ads.us.criteo.com/delivery/r/ajs.php?did=5c560a19383b7ad93bb37508deb03a00&u=%7CHX1eM0zpPitVbf0xT24vaM6U4AiY1TeYgfjDUVVbdu4%3D%7C&c1=eG9IAZIK2MKnlif_A3VZ1-8PEx5_bFVofQVrPPiKhda8JkCsKWBsD2zYvC_F9owWsiKQANPjzJs2iM3m5bCHei3w1zNKxtB3Cx_TBleNKtL5VK1aqyK68XTa0A43qlwLNaStT5NXB3Mz7kx6fDZ20Rh6eAGAW2F9SXVN_7xiLgP288-4OqtK-R7pziZDS04LRUhkL7ohLmAFFyVuwQTREHbpx-4NoonsiQRHKn7ZkuIqZR_rqEewHQ2YowxbI3EOowxo6OV50faWCc7QO5M388FHv8NxeOgOH03LHZT_a2PEKF1xh0-G_qdu5wiyGjJYyPEoNVxB0OaEnDaFVtM7cVaHDm4jrjKlfFhtIGuJb8mg2EeHN0mhUL_0eyv9xWUUQ6osYh3B-jiawHq4592kDDCpS2kYYeqR073IOoRNFNRCR7Fnl0yhIA";
        String encodedValue = "aHR0cHM6Ly9hZHMudXMuY3JpdGVvLmNvbS9kZWxpdmVyeS9yL2Fqcy5waHA%252FZGlkPTVjNTYwYTE5MzgzYjdhZDkzYmIzNzUwOGRlYjAzYTAwJnU9JTdDSFgxZU0wenBQaXRWYmYweFQyNHZhTTZVNEFpWTFUZVlnZmpEVVZWYmR1NCUzRCU3QyZjMT1lRzlJQVpJSzJNS25saWZfQTNWWjEtOFBFeDVfYkZWb2ZRVnJQUGlLaGRhOEprQ3NLV0JzRDJ6WXZDX0Y5b3dXc2lLUUFOUGp6SnMyaU0zbTViQ0hlaTN3MXpOS3h0QjNDeF9UQmxlTkt0TDVWSzFhcXlLNjhYVGEwQTQzcWx3TE5hU3RUNU5YQjNNejdreDZmRFoyMFJoNmVBR0FXMkY5U1hWTl83eGlMZ1AyODgtNE9xdEstUjdwemlaRFMwNExSVWhrTDdvaExtQUZGeVZ1d1FUUkVIYnB4LTROb29uc2lRUkhLbjdaa3VJcVpSX3JxRWV3SFEyWW93eGJJM0VPb3d4bzZPVjUwZmFXQ2M3UU81TTM4OEZIdjhOeGVPZ09IMDNMSFpUX2EyUEVLRjF4aDAtR19xZHU1d2l5R2pKWXlQRW9OVnhCME9hRW5EYUZWdE03Y1ZhSERtNGpyaktsZkZodElHdUpiOG1nMkVlSE4wbWhVTF8wZXl2OXhXVVVRNm9zWWgzQi1qaWF3SHE0NTkya0REQ3BTMmtZWWVxUjA3M0lPb1JORk5SQ1I3Rm5sMHloSUE%253D";
        assertEquals(DeviceUtil.createDfpCompatibleDisplayUrl(displayUrl), encodedValue);
    }

    public void getAdvertisingIdTest() {
        assertNotNull(DeviceUtil.getAdvertisingId(context));
    }

    public void getDeviceModelTest() {
        assertNotNull(DeviceUtil.getDeviceModel());
    }

}
