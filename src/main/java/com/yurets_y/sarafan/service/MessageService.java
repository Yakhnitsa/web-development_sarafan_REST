package com.yurets_y.sarafan.service;

import com.yurets_y.sarafan.domain.Message;
import com.yurets_y.sarafan.domain.User;
import com.yurets_y.sarafan.domain.UserSubscription;
import com.yurets_y.sarafan.domain.Views;
import com.yurets_y.sarafan.dto.EventType;
import com.yurets_y.sarafan.dto.MessagePageDto;
import com.yurets_y.sarafan.dto.MetaDto;
import com.yurets_y.sarafan.dto.ObjectType;
import com.yurets_y.sarafan.repo.MessageRepo;
import com.yurets_y.sarafan.repo.UserSubscriptionRepo;
import com.yurets_y.sarafan.util.WsSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private final String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private final Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private final Pattern IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);


    private final MessageRepo messageRepo;
    private final UserSubscriptionRepo subscriptionRepo;

    private final BiConsumer<EventType,Message> wsSender;

    @Autowired
    public MessageService(
            MessageRepo messageRepo,
            UserSubscriptionRepo subscriptionRepo,
            WsSender sender) {
        this.messageRepo = messageRepo;
        this.subscriptionRepo = subscriptionRepo;
        this.wsSender = sender.getSender(ObjectType.MESSAGE,Views.IdName.class);
    }

    public MessagePageDto findForUser(Pageable pageable, User user) {
        List<User> channels = subscriptionRepo.findBySubscriber(user)
                .stream()
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<Message> page = messageRepo.findByAuthorIn(channels,pageable);
        return new MessagePageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()

        );
    }

    public Message createMessage(Message message, User author) throws IOException {
        message.setCreationTime(LocalDateTime.now());
        message.setAuthor(author);
        fillMetadata(message);
        Message updatedMessage = messageRepo.save(message);

        wsSender.accept(EventType.CREATE,updatedMessage);
        return updatedMessage;
    }
    private void fillMetadata(Message message) throws IOException {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if(matcher.find()){
            String url = text.substring(matcher.start(),matcher.end());
            Matcher imgMatcher = IMG_REGEX.matcher(url);
            message.setLink(url);
            if(imgMatcher.find()){
                message.setLinkCover(url);
            } else if(!url.contains("youtu")){
                MetaDto meta = getMeta(url);

                message.setLinkTitle(meta.getTitle());
                message.setLinkCover(meta.getCover());
                message.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements title = doc.select("meta[name$=title],meta[property$=title]");
        Elements description = doc.select("meta[name$=description],meta[property$=description]");
        Elements cover = doc.select("meta[name$=image],meta[property$=image]");
        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    private String getContent(Element element){
        return element == null ? "" : element.attr("content");
    }

    public Message updateMessage(Message messageFromDB, Message message) {
        BeanUtils.copyProperties(message,messageFromDB,"id");
        try{
            fillMetadata(messageFromDB);
        }catch(IOException e){

        }

        Message updatedMessage = messageRepo.save(messageFromDB);
        wsSender.accept(EventType.UPDATE,updatedMessage);
        return updatedMessage;
    }

    public void delete(Message message) {
        messageRepo.delete(message);
        wsSender.accept(EventType.REMOVE,message);
    }
}
