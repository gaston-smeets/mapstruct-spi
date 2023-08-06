package nl.devgs.mapstruct.spi;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;

public class SnakeCamelCaseAccessorNamingStrategy extends DefaultAccessorNamingStrategy {

    @Override
    public boolean isGetterMethod(final ExecutableElement method) {
        final String methodName = method.getSimpleName().toString();
        return StringUtils.startsWith(methodName, "get") && method.getReturnType().getKind() != TypeKind.VOID;
    }

    @Override
    public boolean isSetterMethod(final ExecutableElement method) {
        final String methodName = method.getSimpleName().toString();
        return StringUtils.startsWith(methodName, "set") && method.getReturnType().getKind() == TypeKind.VOID;
    }

    @Override
    public String getPropertyName(final ExecutableElement getterOrSetterMethod) {
        final String methodName = getterOrSetterMethod.getSimpleName().toString();
        if (StringUtils.contains(methodName, "_")) {
            // snake case -> remove get and underscore
            final String property = StringUtils.substring(methodName, 4);
            return StringUtils.lowerCase(property);
        } else {
            // camel case -> remove get
            final String property = StringUtils.substring(methodName, 3);
            return StringUtils.uncapitalize(property);
        }
    }
}
