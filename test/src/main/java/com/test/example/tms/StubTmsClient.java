package com.test.example.tms;

import org.springframework.stereotype.Component;

@Component
public class StubTmsClient {

    private final static String STUB_TEST_CASE = """
            
            Step 1 - Click on the green "Code" button on the repository page 
            Expected result - A popup opens:
            - Title "Clone" is displayed at the top of the popup. 
            - Two tabs are visible: "HTTPS" (selected by default) and "GitHub CLI". 
            - In HTTPS tab: a text field with the repository URL is shown. 
            - Next to the URL there is a "Copy" button (clipboard icon). 
            - Additional options are listed: "Open with GitHub Desktop" and "Download ZIP". 
            
            Step 2 - Click on the "GitHub CLI" tab in the popup 
            Expected result - The content of the popup switches: 
            - The "GitHub CLI" tab is now highlighted as selected. 
            - A command `gh repo clone <owner>/<repository>` is displayed inside a text field. 
            - A "Copy" button (clipboard icon) is present next to the command. 
            - Below the command there is a helper link "Learn more". 
            
            Step 3 - Click outside of the popup area (for example, on the repository page background)
            Expected result - The popup closes: 
            - The repository page is fully visible again without the clone popup. 
            - The "Code" button remains on the page and can be interacted with again. 
            """;

    public String getTestCaseById(String id) {
        return STUB_TEST_CASE;
    }
}
