import com.ttnd.ConstantEnum
import com.ttnd.DocumentResource
import com.ttnd.LinkResource
import com.ttnd.ReadingItem
import com.ttnd.Resource
import com.ttnd.Subscription
import com.ttnd.Topic
import com.ttnd.User
import java.util.Random

class BootStrap {

    def init = { servletContext ->

      /*  User user = new User(name:'testtt1',address: 'test1address',password: '123456')
        println user.validate();
        user.save(flus:true,failOnError: true)
         user = new User(name:'testtt1',address: 'test1address',password: 'testtt1')
        println user.validate();
        user.save(flus:true,failOnError: true)
*/



        createUser()

    }

    void createUser(){

        User user1= new User(userName: 'Priya',email: 'priya@gmail.com',firstName: 'priya',lastName: 'bhadani',admin:false,password: 'abcde',active: true)
        user1.validate()
        createTopics(user1)
        user1= new User(userName: 'richa',email: 'richa@gmail.com',firstName: 'richa',lastName: 'bhadani',admin:true,password: 'abcde',active: true)
        user1.validate()
        createTopics(user1)


    }
    void createTopics(User user){
        (1..5).each {
            Topic topic = new Topic(name: "Test$it",createdBy: user,visibility: ConstantEnum.Visibility.PUBLIC)
            topic.validate()
            user.addToCreatedTopics(topic)
            user.save(flush: true,failOnError: true)
            subscribeTopic(topic,user)
           createResource(topic,user)

        }

    }

    void createResource(Topic topic,User user){
        createLinkResource(topic,user)
        createDocumentResource(topic,user)
       createReadingItems(user)
    }
    void createLinkResource(Topic topic,User user){
        (1..5).each {
            LinkResource lresource = new LinkResource(url: 'http://www.google.com',topic:topic,createdBy:user,title: "LinkResource title $it",description: "Demo test for first title$it")
            lresource.validate()
            lresource.save(flush: true, failOnError: true)

        }
    }

void createDocumentResource(Topic topic,User user){
    (1..5).each {
        DocumentResource dresource = new DocumentResource(filePath: "/home/ttnd/Desktop/assignment2.txt",topic:topic,createdBy:user,title: "Document resource Title $it",description: "Demo test for first title$it")
        dresource.validate()
        dresource.save(flush: true, failOnError: true)

    }


}
        void createReadingItems(User user1){

            Random rand = new Random()
            int max = 10
            (1..3).each {
                long num =rand.nextInt(max+1)
                if (num==0) { num =1}
             Resource resource = Resource.findById(num)
                print "$num ------- $resource----$user1"
                ReadingItem r =new ReadingItem(user:user1,resource: resource,isRead: true)
                r.validate()
                r.save(flush: true, failOnError: true)
            }




    }

    void subscribeTopic(Topic topic,User user){
        Subscription subsctopic = new Subscription(user:user,topic:topic,seriousness: ConstantEnum.Seriousness.SERIOUS)
        subsctopic.validate()
        subsctopic.save(flush: true, failOnError: true)

    }


    def destroy = {
    }
}
