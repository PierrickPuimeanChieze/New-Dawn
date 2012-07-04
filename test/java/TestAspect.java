

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
@Aspect
public class TestAspect {

    @DeclareParents(value = "com.newdawn.model.system.Asteroid+",
    defaultImpl = TestInterfaceImp.class)
    public static TestInterface mixin;

    @Before("com.xyz.myapp.SystemArchitecture.businessService() &&"
    + "this(usageTracked)")
    public void testMethod() {
        System.out.println(mixin.getClass());
    }
}
