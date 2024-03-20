package com.zamzar.api.examples.client;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;

public class Credits {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Make an API call to get the remaining credits
        int remainingCredits = zamzar.getProductionCreditsRemaining();
        int remainingTestCredits = zamzar.getSandboxCreditsRemaining();
        System.out.println("You currently have " + remainingCredits + " production credits remaining and " + remainingTestCredits + " test credits remaining.");

        // Or, if you've made an API call already (such as to convert a file), retrieve the last known credits
        remainingCredits = zamzar.getLastProductionCreditsRemaining();
        remainingTestCredits = zamzar.getLastSandboxCreditsRemaining();
        System.out.println("At the time of your last request, you had " + remainingCredits + " production credits remaining and " + remainingTestCredits + " test credits remaining.");
    }
}
