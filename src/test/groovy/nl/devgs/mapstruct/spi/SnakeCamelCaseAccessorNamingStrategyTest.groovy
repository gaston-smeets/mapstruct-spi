package nl.devgs.mapstruct.spi

import spock.lang.Specification

import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Name
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

class SnakeCamelCaseAccessorNamingStrategyTest extends Specification {

    def strategy = new SnakeCamelCaseAccessorNamingStrategy()

    def method = Mock(ExecutableElement)
    def name = Mock(Name)
    def returnType = Mock(TypeMirror)

    def setup() {
        method.simpleName >> name
        method.returnType >> returnType
    }

    def 'A camel case getter is recognized'() {
        given: 'A camel case getter'
        name.toString() >> 'getName'
        def typeKind = TypeKind.ARRAY
        returnType.kind >> typeKind

        when: 'It is checked if it is a getter'
        def isGetterMethod = strategy.isGetterMethod(method)

        then: 'It is a getter'
        isGetterMethod
    }

    def 'A snake case getter is recognized'() {
        given: 'A snake case getter'
        name.toString() >> 'get_name'
        def typeKind = TypeKind.ARRAY
        returnType.kind >> typeKind

        when: 'It is checked if it is a getter'
        def isGetterMethod = strategy.isGetterMethod(method)

        then: 'It is a getter'
        isGetterMethod
    }

    def 'A camel case setter is recognized'() {
        given: 'A camel case setter'
        name.toString() >> 'setName'
        def typeKind = TypeKind.VOID
        returnType.kind >> typeKind

        when: 'It is checked if it is a setter'
        def isSetterMethod = strategy.isSetterMethod(method)

        then: 'It is a setter'
        isSetterMethod
    }

    def 'A snake case setter is recognized'() {
        given: 'A snake case setter'
        name.toString() >> 'set_name'
        def typeKind = TypeKind.VOID
        returnType.kind >> typeKind

        when: 'It is checked if it is a setter'
        def isSetterMethod = strategy.isSetterMethod(method)

        then: 'It is a setter'
        isSetterMethod
    }

    def 'A camel case property is correctly determined from a getter'() {
        given: 'A camel case getter'
        name.toString() >> 'getNameOfPerson'

        when: 'the property is retrieved from the method'
        def property = strategy.getPropertyName(method)

        then: 'The property is correct'
        property == 'nameOfPerson'
    }

    def 'A snake case property is correctly determined from a getter'() {
        given: 'A snake case getter'
        name.toString() >> 'get_name_of_person'
        def typeKind = TypeKind.ARRAY
        returnType.kind >> typeKind

        when: 'the property is retrieved from the method'
        def property = strategy.getPropertyName(method)

        then: 'The property is correct'
        property == 'name_of_person'
    }

    def 'A camel case property is correctly determined from a setter'() {
        given: 'A camel case setter'
        name.toString() >> 'setNameOfPerson'

        when: 'the property is retrieved from the method'
        def property = strategy.getPropertyName(method)

        then: 'The property is correct'
        property == 'nameOfPerson'
    }

    def 'A snake case property is correctly determined from a setter'() {
        given: 'A snake case setter'
        name.toString() >> 'set_name_of_person'

        when: 'the property is retrieved from the method'
        def property = strategy.getPropertyName(method)

        then: 'The property is correct'
        property == 'name_of_person'
    }
}
