//package br.inatel.pos.dm111.vfr.config;
//
//
//import com.google.api.client.util.Value;
//import com.google.cloud.pubsub.v1.Publisher;
//import com.google.pubsub.v1.ProjectSubscriptionName;
//import com.google.pubsub.v1.TopicName;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import java.io.IOException;
//
//@Configuration
//public class PubSubConfiguration {
//
//    @Value("${vale-food.gae.project-id}")
//    private String projectId;
//
//    @Value("${vale-food.gae.users.subscription-name}")
//    private String usersSubscriptionName;
//
//    @Bean
//    public ProjectSubscriptionName subscriptionName() {
//        return ProjectSubscriptionName.of(projectId,usersSubscriptionName);
//    }
//
//    @Bean
//    public Publisher publisher(TopicName topicName) throws IOException {
//        return Publisher.newBuilder(topicName).build();
//    }
//
//}
