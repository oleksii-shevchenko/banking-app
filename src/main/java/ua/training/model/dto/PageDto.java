package ua.training.model.dto;

import java.util.List;

/**
 * This is generic class pages in pagination. It produced by appropriate dao.
 * @param <T> The type on items that displayed on JSP page.
 */
public class PageDto<T> {
    private List<T> items;
    private int itemsNumber;
    private int pagesNumber;
    private int currentPage;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getItemsNumber() {
        return itemsNumber;
    }

    public void setItemsNumber(int itemsNumber) {
        this.itemsNumber = itemsNumber;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
