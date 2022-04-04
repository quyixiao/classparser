package com.jvm;


import com.jvm.classfile.*;
import com.jvm.utils.*;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Data
public class ClassParser {

    public boolean helpFlag;
    public boolean versionFlag;
    public String cpOption;
    public String jclass;
    public String[] args = new String[0];
    public String XjreOption;

    public boolean verboseInstFlag;


    public static void main(String[] args)  throws Exception{
        String className = "com.jvm.test.QYXTest";
        String resourcePath =
                ClassUtils.convertClassNameToResourcePath(className) + ClassUtils.CLASS_FILE_SUFFIX;
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        System.out.println(resourcePath);
        InputStream is = getInputStream(classLoader,resourcePath);
        ClassFile classFile  = ClassFile.Parse(ClassReaderUtils.readClass(is));
        for(MemberInfo memberInfo:classFile.getMethods()){
            System.out.println(memberInfo.Name());
            System.out.println(memberInfo.Descriptor());
            MethodDescriptorParser parser = new MethodDescriptorParser();
            MethodDescriptor parsedDescriptor = parser.parseMethodDescriptor(memberInfo.Descriptor());
            for(String parameterType: parsedDescriptor.parameterTypes){
                String b = ClassNameHelper.toStandClassName(parameterType);
                System.out.println(b);
            }
            AttributeInfo attributeInfos [] = memberInfo.getAttributes();
            for(AttributeInfo codeAttribute:attributeInfos ){
                if(codeAttribute instanceof CodeAttribute){
                    AttributeInfo codeAttributeInfos[] = ((CodeAttribute) codeAttribute).getAttributes();
                    for(AttributeInfo attributeInfo:codeAttributeInfos){
                        if(attributeInfo instanceof  LineNumberTableAttribute){
                            int i = ((LineNumberTableAttribute) attributeInfo).GetFirstLineNumber();
                            System.out.println(i);
                        }
                    }
                }
            }
            System.out.println("------------------");
        }
    }

    public static InputStream getInputStream(ClassLoader classLoader, String path) throws IOException {
        InputStream is;

        if (classLoader != null) {
            is = classLoader.getResourceAsStream(path);
        } else {
            is = ClassLoader.getSystemResourceAsStream(path);
        }
        if (is == null) {
            throw new FileNotFoundException(" cannot be opened because it does not exist");
        }
        return is;
    }

}
