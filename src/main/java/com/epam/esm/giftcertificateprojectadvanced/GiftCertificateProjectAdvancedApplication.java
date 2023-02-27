package com.epam.esm.giftcertificateprojectadvanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.epam.esm")
@EntityScan("com.epam.esm")
@EnableJpaRepositories("com.epam.esm")
@EnableJpaAuditing
@SpringBootApplication
public class GiftCertificateProjectAdvancedApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiftCertificateProjectAdvancedApplication.class,args);
    }

//    @Bean
//    public CommandLineRunner generateData(GiftCertificateRepository giftCertificateRepository,
//                                          TagRepository tagRepository,
//                                          UserRepository userRepository,
//                                          OrderService orderService){
//        return args -> {
//
//            List<Tag> tags = new ArrayList<>();
//            for(int i = 1; i<=1000; i++){
//                Tag tag = new Tag(String.format("tag%d",i));
//                tags.add(tag);
//            }
//            tagRepository.saveAll(tags);
//
//            for (int i = 1;i<=10000;i++){
//                GiftCertificateGenerator generator = new GiftCertificateGenerator();
//                Thread thread = new Thread(generator);
//                thread.start();
//                thread.join();
//                GiftCertificate giftCertificate = generator.getGiftCertificate();
//                System.out.println(giftCertificate);
//                giftCertificateRepository.save(giftCertificate);
//            }
//
//            for(int i = 1; i<=1000;i++){
//                User user = UserGenerator.generateRandomUser(i);
//                userRepository.save(user);
//            }
//            for(int i = 1; i<=10000;i++){
//                Random random = new Random();
//                orderService.addNewOrder(
//                        random.nextLong(1,1000),
//                        random.nextLong(1,10000)
//                );
//            }
//        };
//
//        };
}
