package database;

import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.Helpers;
import input.CredentialsInput;

public class Credentials implements JSONable{
    private String name;
    private String password;
    public enum AccountType {
        standard,
        premium
    };
    private AccountType accountType;
    private String country;
    private int balance;

    public Credentials(CredentialsInput credentialsInput) {
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public ObjectNode toJSON() {
        ObjectNode output = Helpers.objectMapper.createObjectNode();
        
        output.put("name", this.name);
        output.put("password", this.password);
        output.put("accountType", this.accountType.toString());
        output.put("country", this.country);
        output.put("balance", String.valueOf(this.balance));
        
        return output;
    }

    
}
