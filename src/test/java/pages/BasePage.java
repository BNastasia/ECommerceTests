package pages;

import com.microsoft.playwright.Page;

abstract class BasePage {
    private final Page page;

    BasePage(Page page) {
        this.page = page;
    }

    Page getPage() {
        return page;
    }
}
