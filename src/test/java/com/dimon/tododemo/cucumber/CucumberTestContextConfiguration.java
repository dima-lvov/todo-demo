package com.dimon.tododemo.cucumber;

import com.dimon.tododemo.TododemoApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = TododemoApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
