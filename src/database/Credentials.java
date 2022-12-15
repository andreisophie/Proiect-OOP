package database;

import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.Helpers;
import input.CredentialsInput;

public class Credentials implements JSONable {
    private String name;
    private String password;
    public enum AccountType {
        standard,
        premium
    };
    private AccountType accountType;
    private String country;
    private int balance;

    public Credentials(final CredentialsInput credentialsInput) {
        this.name = credentialsInput.getName();
        this.password = credentialsInput.getPassword();
        switch (credentialsInput.getAccountType()) {
            case "premium":
                accountType = AccountType.premium;
                break;
            case "standard":
            default:
                accountType = AccountType.standard;
                break;
        }
        this.country = credentialsInput.getCountry();
        this.balance = Integer.parseInt(credentialsInput.getBalance());
    }

    /**
     * @return name of the user described by these credentials
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name in this instance to a new value
     * @param name new value to be set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return password of the user described by these credentials
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password in this instance to a new value
     * @param name new password to be set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return account type of the user described by these credentials
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Sets the account type in this instance to a new value
     * @param name new account type to be set
     */
    public void setAccountType(final AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * @return country of the user described by these credentials
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country in this instance to a new value
     * @param name new country to be set
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * @return balance of the user described by these credentials
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets the balance in this instance to a new value
     * @param name new balance to be set
     */
    public void setBalance(final int balance) {
        this.balance = balance;
    }

    /**
     * Returns a JsonNode object which contains relevant data from this class
     * To be used for output
     */
    @Override
    public ObjectNode toJSON() {
        final ObjectNode output = Helpers.OBJECT_MAPPER.createObjectNode();

        output.put("name", this.name);
        output.put("password", this.password);
        output.put("accountType", this.accountType.toString());
        output.put("country", this.country);
        output.put("balance", String.valueOf(this.balance));

        return output;
    }
}
