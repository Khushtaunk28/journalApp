package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query = new Query();
        //                                            .regex("[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}"));
        query.addCriteria(Criteria.where("email"));
        query.addCriteria(Criteria.where("sentimentAnalysis").exists(true));
        //query.addCriteria(Criteria.where("username").is("vipul"));
        return mongoTemplate.find(query, User.class);
    }


}
