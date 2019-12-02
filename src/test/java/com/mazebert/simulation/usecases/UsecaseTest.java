package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimTest;
import org.junit.jupiter.api.BeforeEach;
import org.jusecase.VoidUsecase;
import org.jusecase.util.GenericTypeResolver;

public class UsecaseTest<Request> extends SimTest {
    protected VoidUsecase<Request> usecase;
    protected Request request;

    @BeforeEach
    void initRequest() {
        createRequest();
    }

    @SuppressWarnings({"unchecked"})
    public void createRequest() {
        try {
            Class<?> requestClass = GenericTypeResolver.resolve(UsecaseTest.class, getClass(), 0);
            request = (Request) requestClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate request. You need to override createRequest() and do it manually.", e);
        }
    }

    protected void whenRequestIsExecuted() {
        usecase.execute(request);
    }
}
