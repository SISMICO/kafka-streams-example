package br.com.sismico.kafkaflink;

public class Address {

    private String clientId;
    private String address;

    public Address(String clientId, String address) {
        this.clientId = clientId;
        this.address = address;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
