package com.example.personalfitnesstrainer;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestEmailChanger.class,
        TestFakeServices.class,
        TestTimeConverter.class
})
public class UnitTests
{

}