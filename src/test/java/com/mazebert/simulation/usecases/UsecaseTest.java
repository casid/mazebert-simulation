package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimTest;
import net.jodah.typetools.TypeResolver;
import org.junit.jupiter.api.BeforeEach;
import org.jusecase.VoidUsecase;

public class UsecaseTest<Request> extends SimTest {
    protected VoidUsecase<Request> usecase;
    protected Request request;
    protected Throwable error;

    @BeforeEach
    void initRequest() {
        createRequest();
    }

    @SuppressWarnings("unchecked")
    public void createRequest() {
        try {
            Class<?> requestClass = TypeResolver.resolveRawArguments(UsecaseTest.class, getClass())[0];
            request = (Request) requestClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate request. You need to override createRequest() and do it manually.", e);
        }
    }

    protected void whenRequestIsExecuted() {
        usecase.execute(request);
    }

    protected void whenErrorRequestIsExecuted() {
        try {
            whenRequestIsExecuted();
        } catch (Throwable e) {
            error = e;
        }
    }
}
