package by.olegyev.easychain;

import java.util.Objects;

public class EasyTransaction {

    private String sender;
    private String recipient;
    private float value;

    public EasyTransaction(final String sender, final String recipient, final float value) {
        this.sender = sender;
        this.recipient = recipient;
        this.value = value;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        EasyTransaction that = (EasyTransaction) o;
        return Float.compare(that.value, value) == 0 &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(recipient, that.recipient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recipient, value);
    }

    @Override
    public String toString() {
        return "EasyTransaction{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", value=" + value +
                '}';
    }

}