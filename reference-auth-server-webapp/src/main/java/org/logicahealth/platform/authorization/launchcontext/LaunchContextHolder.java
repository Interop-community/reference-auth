package org.logicahealth.platform.authorization.launchcontext;

import org.logicahealth.platform.security.LaunchContext;

public interface LaunchContextHolder {

    LaunchContext getLaunchContext(String launchContextId);

    LaunchContext putLaunchContext(String launchContextId, LaunchContext launchContext);

    LaunchContext removeLaunchContext(String launchContextId);
}
