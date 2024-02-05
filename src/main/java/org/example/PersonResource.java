package org.example;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Timestamp;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.raw.Person;
import org.example.raw.PersonProto;
import org.jboss.logging.Logger;

import java.util.Optional;

@Path("/person")
@RegisterForReflection(targets = {Person.class, PersonProto.class, Person.Builder.class, com.google.protobuf.Timestamp.class, Timestamp.Builder.class})
public class PersonResource {

    private static final Logger LOG = Logger.getLogger(PersonResource.class);

    private Person person;

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response setPerson(byte[] personData) {
        LOG.info("Received person data");
        try {
            person = Person.parseFrom(personData);
            LOG.info("The person: " + person.toString());
            return Response.status(201).build();
        } catch (InvalidProtocolBufferException e) {
            LOG.error("Failed to parse person data", e);
            return Response.status(500).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] getPerson() {
        LOG.info("Get person data");
        return Optional.ofNullable(person).map(Person::toByteArray).orElse(new byte[0]);
    }
}
