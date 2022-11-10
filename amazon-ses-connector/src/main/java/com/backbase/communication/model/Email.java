package com.backbase.communication.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Email {
    private List<String> to = new ArrayList<>();
    private List<String> cc = new ArrayList<>();
    private List<String> bcc = new ArrayList<>();
    private String from;
    private String replyTo;
    private String body;
    private String subject = "";
    private Boolean important = false;
    private List<Attachment> attachments = new ArrayList<>();
    private Map<String, String> additions = new HashMap<>();
}
