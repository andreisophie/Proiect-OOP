package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.GlobalState;

public class LoggedOutHomepage extends Page {
    // supraincarc metoda changePage din Page
    public ObjectNode changePage(LoginPage target) {
        GlobalState.getInstance().setCurrentPage(target);
        return null;
    }

    public ObjectNode changePage(RegisterPage target) {
        GlobalState.getInstance().setCurrentPage(target);
        return null;
    }
    
}
