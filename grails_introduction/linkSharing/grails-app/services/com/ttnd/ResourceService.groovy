package com.ttnd

import co.ResourceCO
import grails.transaction.Transactional

@Transactional
class ResourceService {

    def serviceMethod() {

    }
    def saveLinkResource(LinkResource linkResource) {

        linkResource.validate()

        if (!linkResource.hasErrors()) {
            linkResource.save(flush:true)
            ResourceRating resourceRating1 = new ResourceRating(user: linkResource.createdBy, resource: linkResource, score: 2)
            resourceRating1.save(flush: true, failOnError: true)
            linkResource.addToResourceRatings(resourceRating1)
        }
        else{
            println linkResource.errors
        }
    }
    def saveDocResource(ResourceCO resourceCO,User user) {
        if (!resourceCO.hasErrors()) {
            Resource documentResource = new DocumentResource()
            documentResource.properties = resourceCO.properties
            documentResource.createdBy=user
            documentResource.validate()
            documentResource.save(flush:true)
            Topic t = resourceCO.topic
            t.addToResources(documentResource)
            t.merge(flush: true)
            ResourceRating resourceRating1 = new ResourceRating(user: documentResource.createdBy, resource: documentResource, score: 2)
            resourceRating1.save(flush: true, failOnError: true)
            documentResource.addToResourceRatings(resourceRating1)
            println "at last"+t.resources.size()
        }
        else{
            println resourceCO.errors
        }
    }

    def markRead(Map params) {
        Resource resource = Resource.load(params.resId)
        User user = User.load(params.uId)
        ReadResources readResources = ReadResources.findByUserAndResource(user,resource)
        if(!readResources){
            println "in If"
            readResources = new ReadResources(resource: resource,user:user,readFlg: true)
            readResources.save(flush: true)
            resource.addToReadResources(readResources)
            resource.merge()
            println "new Read resource"+readResources.properties
        }
        else {
            readResources.readFlg = true
            readResources.merge()
            println "Read resource"+readResources.properties
        }
    }
}
