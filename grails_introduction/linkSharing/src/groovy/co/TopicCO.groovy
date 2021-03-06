package co

import grails.validation.Validateable
import com.ttnd.Subscription
import com.ttnd.Topic
import  com.ttnd.User


@Validateable
class TopicCO {
    String name
    Integer createdById
    enum VisibilityValue {PUBLIC,PRIVATE}
    VisibilityValue visibility
    Subscription.SeriousnessValue seriousness
    static constraints = {
        name size:5..15,blank:false,validator:{val,obj->
            User user = User.get(obj.createdById)
            if(!Topic.findByNameAndCreatedBy(val,user)){
                return true
            }
            else {
                return false
            }
        }
    }
}
