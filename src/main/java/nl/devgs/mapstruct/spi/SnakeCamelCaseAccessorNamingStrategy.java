package nl.devgs.mapstruct.spi;

import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;

public class SnakeCamelCaseAccessorNamingStrategy extends DefaultAccessorNamingStrategy {

    @Override
    public boolean isGetterMethod(final ExecutableElement method) {
        final String methodName = method.getSimpleName().toString();
        return methodName.startsWith("get") && method.getReturnType().getKind() != TypeKind.VOID;
    }

    @Override
    public boolean isSetterMethod(final ExecutableElement method) {
        final String methodName = method.getSimpleName().toString();
        return methodName.startsWith( "set") && method.getReturnType().getKind() == TypeKind.VOID;
    }

    @Override
    public String getPropertyName(final ExecutableElement getterOrSetterMethod) {
        final String methodName = getterOrSetterMethod.getSimpleName().toString();
        if (methodName.contains("_")) {
            // snake case -> remove get and underscore
            final String property = methodName.substring(4);
            return property.toLowerCase();
        } else {
            // camel case -> remove get
            final String property = methodName.substring(3);
            return Character.toLowerCase(property.charAt(0)) + property.substring(1);
        }
    }
}
