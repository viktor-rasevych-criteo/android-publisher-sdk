package com.criteo.publisher.privacy.gdpr;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.criteo.publisher.Util.SafeSharedPreferences;

/**
 * https://github.com/InteractiveAdvertisingBureau/GDPR-Transparency-and-Consent-Framework/tree/master/TCFv2
 */
public class Tcf2GdprStrategy implements TcfGdprStrategy {

  private static final int GDPR_APPLIES_UNSET = -1;

  @VisibleForTesting
  static final String IAB_TCString_Key = "IABTCF_TCString";

  @VisibleForTesting
  static final String IAB_GDPR_APPLIES_KEY = "IABTCF_gdprApplies";

  private final SafeSharedPreferences safeSharedPreferences;

  public Tcf2GdprStrategy(SafeSharedPreferences safeSharedPreferences) {
    this.safeSharedPreferences = safeSharedPreferences;
  }

  @Override
  @NonNull
  public String getConsentString() {
    return safeSharedPreferences.getString(IAB_TCString_Key, "");
  }

  @Override
  @NonNull
  public String getSubjectToGdpr() {
    int gdprApplies = safeSharedPreferences.getInt(IAB_GDPR_APPLIES_KEY, GDPR_APPLIES_UNSET);
    return String.valueOf(gdprApplies);
  }

  @Override
  @NonNull
  public Integer getVersion() {
    return 2;
  }

  @Override
  public boolean isProvided() {
    String subjectToGdpr = getSubjectToGdpr();
    String consentString = getConsentString();
    boolean isSubjectToGdprEmpty = Integer.valueOf(subjectToGdpr).equals(GDPR_APPLIES_UNSET);
    boolean isConsentStringEmpty = consentString.isEmpty();

    return !isSubjectToGdprEmpty && !isConsentStringEmpty;
  }
}
