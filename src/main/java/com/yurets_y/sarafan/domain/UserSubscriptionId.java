package com.yurets_y.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSubscriptionId {
    @JsonView(Views.Id.class)
    private String channelId;

    @JsonView(Views.Id.class)
    private String subscriberId;

    public UserSubscriptionId(String channelId, String subscriberId) {
        this.channelId = channelId;
        this.subscriberId = subscriberId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }
}
