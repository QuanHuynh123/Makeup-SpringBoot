/*
package com.example.Makeup.config;

import com.example.Makeup.entity.Category;
import com.example.Makeup.entity.Role;
import com.example.Makeup.entity.ServiceMakeup;
import com.example.Makeup.entity.SubCategory;
import com.example.Makeup.repository.CategoryRepository;
import com.example.Makeup.repository.RoleRepository;
import com.example.Makeup.repository.ServiceMakeupRepository;
import com.example.Makeup.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ServiceMakeupRepository serviceMakeupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            // Tạo vai trò nếu chưa tồn tại
            Role adminRole = new Role(1,"ADMIN");
            Role userRole = new Role(2,"USER");
            Role staffRole = new Role(3,"STAFF");
            roleRepository.saveAll(List.of(adminRole, userRole,staffRole));

 //////////////////////SERVICEMAKEUP////////////////////////

            ServiceMakeup serviceMakeup = new ServiceMakeup("Makeup Đám Cưới","s",500);
            ServiceMakeup serviceMakeup1 = new ServiceMakeup("Makeup Đi Chơi/Event","s",500);
            ServiceMakeup serviceMakeup2 = new ServiceMakeup("Makeup Mẫu Ảnh","s",500);
            ServiceMakeup serviceMakeup3 = new ServiceMakeup("Makeup Model","s",500);
            ServiceMakeup serviceMakeup4 = new ServiceMakeup("Makeup Công Sở","s",500);
            ServiceMakeup serviceMakeup5 = new ServiceMakeup("Makeup Sexy","s",500);
            serviceMakeupRepository.saveAll(List.of(serviceMakeup,serviceMakeup1,serviceMakeup2,serviceMakeup3,serviceMakeup4,serviceMakeup5));

 /////////////////////////CATEGORY////////////////////

            Category anime = new Category("ANIME");
            Category movie = new Category("MOVIE");
            Category game = new Category("GAME");
            Category festival = new Category("FESTIVAL");
            categoryRepository.saveAll(List.of(anime,movie,game,festival));

 ///////////////////////SUBCATEGORY/////////////////////////

            SubCategory dragonBall = new SubCategory("DRAGON BALL",1);
            SubCategory attackOnTitan = new SubCategory("ATTACK ON TITAN",1);
            SubCategory naruto = new SubCategory("NARUTO",1);
            SubCategory onePeace = new SubCategory("ONE PEACE ",1);
            SubCategory onePunchMan = new SubCategory("ONE PUNCH MAN",1);
            SubCategory jujutsuKaisen = new SubCategory("JUJUTSU KAISEN",1);

            SubCategory residentEvil = new SubCategory("RESIDENT EVIL",2);
            SubCategory superman = new SubCategory("SUPERMAN",2);
            SubCategory spider = new SubCategory("SPIDER-MAN ",2);
            SubCategory aquaman = new SubCategory("AQUAMAN",2);
            SubCategory it = new SubCategory("IT",2);
            SubCategory captain = new SubCategory("CAPTAIN MAVEL",2);

            SubCategory fateGrandOrder = new SubCategory("FATE GRAND ORDER",3);
            SubCategory league = new SubCategory("LEAGUE OF LEGION",3);
            SubCategory honkaiStarRail = new SubCategory("HONKAI STAR RAIL",3);
            SubCategory genshinImpact = new SubCategory("GENSHIN IMPACT ",3);
            SubCategory vanlorant = new SubCategory("VANLORANT",3);

            SubCategory halloween = new SubCategory("HALLOWEEN",4);
            SubCategory merryChristmas = new SubCategory("MERRY CHRISTMAS",4);

            subCategoryRepository.saveAll(List.of(dragonBall,attackOnTitan,naruto,onePeace,onePunchMan,jujutsuKaisen));
            subCategoryRepository.saveAll(List.of(residentEvil,superman,spider,aquaman,it,captain));
            subCategoryRepository.saveAll(List.of(fateGrandOrder,league,honkaiStarRail,genshinImpact,vanlorant));
            subCategoryRepository.saveAll(List.of(halloween,merryChristmas));
            System.out.println("Data initialized successfully.");
        };
    }
}
*/
