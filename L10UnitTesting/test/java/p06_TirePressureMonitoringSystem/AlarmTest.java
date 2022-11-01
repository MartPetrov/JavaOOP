package p06_TirePressureMonitoringSystem;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AlarmTest {

    private static final double LOWER_VALUE = 15.0;
    private static final double HIGHER_VALUE = 22.5;
    private static final double NORMAL_VALUE = 18.4;
    // Ако налягането е под 17 алармата трябва да е включена
    @Test
    public void testAlarmWithLowerValue() {
        //налягането = 15
        Sensor sensor = Mockito.mock(Sensor.class);
        Mockito.when(sensor.popNextPressurePsiValue()).thenReturn(LOWER_VALUE);
        Alarm alarm = new Alarm(sensor);
        alarm.check();
        Assert.assertTrue(alarm.getAlarmOn());
    }

    // Ако налягането е над 21 алармата трябва да е включена
    @Test
    public void testAlarmWithHigherValue() {
        //налягането = 22.5
        Sensor sensor = Mockito.mock(Sensor.class);
        Mockito.when(sensor.popNextPressurePsiValue()).thenReturn(HIGHER_VALUE);
        Alarm alarm = new Alarm(sensor);
        alarm.check();
        Assert.assertTrue(alarm.getAlarmOn());
    }

    // Ако налягането е между 17 и 21 алармата трябва да е изключена
    @Test
    public void testAlarmWithNormalValue() {
        //налягането = 18.9
        Sensor sensor = Mockito.mock(Sensor.class);
        Mockito.when(sensor.popNextPressurePsiValue()).thenReturn(NORMAL_VALUE);
        Alarm alarm = new Alarm(sensor);
        alarm.check();
        Assert.assertFalse(alarm.getAlarmOn());
    }

}