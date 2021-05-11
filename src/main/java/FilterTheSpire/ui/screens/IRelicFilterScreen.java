package FilterTheSpire.ui.screens;

public interface IRelicFilterScreen {
    void selectAll();
    void invertAll();
    void refreshFilters();
    void clearAll();
    void selectOnly(String relicID);
}
