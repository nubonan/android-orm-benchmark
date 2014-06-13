package com.littleinc.orm_benchmark.active;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by ada on 13.06.14.
 */

@Table(name = "message")
public class Message extends Model{

    @Column(name = "content")
    private String mContent;

    @Column(name = "client_id")
    private Long mClientId;

    @Column(name = "created_at")
    private Integer mCreatedAt;

    @Column(name = "sorted_by")
    private Double mSortedBy;

    @Column(name = "command_id", index = true)
    private Long mCommandId;

    @Column(name = "sender_id", notNull = true)
    private long mSenderId;

    @Column(name = "channel_id", notNull = true)
    private long mChannelId;

    private List<User> readers;

    public Message() {
        super();
    }

    public List<User> items() {
        return getMany(User.class, "user");
    }


    public Message( String content, Long client_id, Integer created_at, Double sorted_by, Long command_id, long sender_id, long channel_id) {
        this.mContent = content;
        this.mClientId = client_id;
        this.mCreatedAt = created_at;
        this.mSortedBy= sorted_by;
        this.mCommandId = command_id;
        this.mSenderId = sender_id;
        this.mChannelId = channel_id;
    }



    public long getClientId() {
        return mClientId;
    }

    public void setClientId(long clientId) {
        this.mClientId = clientId;
    }

    public long getCommandId() {
        return mCommandId;
    }

    public void setCommandId(long commandId) {
        this.mCommandId = commandId;
    }

    public double getSortedBy() {
        return mSortedBy;
    }

    public void setSortedBy(double sortedBy) {
        this.mSortedBy = sortedBy;
    }

    public int getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(int createdAt) {
        this.mCreatedAt = createdAt;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public long getSenderId() {
        return mSenderId;
    }

    public void setSenderId(long senderId) {
        this.mSenderId = senderId;
    }

    public long getChannelId() {
        return mChannelId;
    }

    public void setChannelId(long channelId) {
        this.mChannelId = channelId;
    }
}
