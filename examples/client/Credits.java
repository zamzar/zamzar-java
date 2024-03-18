package client;

import com.zamzar.api.ZamzarClient;

public class Credits {
    public static void main(String[] args) {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Make an API call to get the remaining credits
        int remainingCredits = zamzar.getLastProductionCreditsRemaining();
        int remainingTestCredits = zamzar.getLastSandboxCreditsRemaining();

        // Or, if you've made an API call already (such as to convert a file), retrieve the last known credits
        remainingCredits = zamzar.getLastProductionCreditsRemaining();
        remainingTestCredits = zamzar.getLastSandboxCreditsRemaining();
    }
}
