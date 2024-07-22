/*
package com.edureka.sagaorchestrator.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.Arrays;
import java.util.HashSet;

@RequiredArgsConstructor
@Configuration
@EnableStateMachineFactory
public class MyStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {
    private final Action<String, String> e1Action;
    private final Action<String, String> e2Action;
    private final Action<String, String> e3Action;


    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states.withStates()
                .initial("SI")
                .states(new HashSet<String>(Arrays.asList("S1", "S2", "S3")))
                .end("SF");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions.withExternal()
                .source("SI").target("S1").event("E1")
                .action(e1Action)
                .and().withExternal()
                .source("S1").target("S2").event("E2")
                .action(e2Action)
                .and().withExternal()
                .source("S2").target("SF").event("end")
                .action(e3Action)

        ;
    }


}
*/
