package com.in28minutes.powermock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

import java.lang.annotation.Inherited;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.mockito.Mockito.*;
@RunWith(PowerMockRunner.class)
@PrepareForTest({ UtilityClass.class})
public class PowerMockitoMockingStaticMethodTest {

	@Mock
	Dependency dependencyMock;

	@InjectMocks
	SystemUnderTest systemUnderTest;

	@Test
	public void powerMockito_MockingAStaticMethodCall() {
		when(dependencyMock.retrieveAllStats()).thenReturn(Arrays.asList(1,2,3));
		PowerMockito.mockStatic(UtilityClass.class);
		when(UtilityClass.staticMethod(6)).thenReturn(60);
		assertEquals(60,systemUnderTest.methodCallingAStaticMethod());
		PowerMockito.verifyStatic();
		UtilityClass.staticMethod(6);
	}
}