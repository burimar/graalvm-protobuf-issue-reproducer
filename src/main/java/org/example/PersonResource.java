package org.example;

import com.google.protobuf.InvalidProtocolBufferException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.raw.Person;

import java.util.Optional;

@Path("/person")
public class PersonResource {

    private Person person;

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response setPerson(byte[] personData) {
        try {
            person = Person.parseFrom(personData);
            return Response.status(201).build();
        } catch (InvalidProtocolBufferException e) {
            return Response.status(400).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] getPerson() {
        return Optional.ofNullable(person).map(Person::toByteArray).orElse(new byte[0]);
    }
}
