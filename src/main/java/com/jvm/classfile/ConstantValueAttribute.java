package com.jvm.classfile;

import com.jvm.data.Uint16;

/*
ConstantValue_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u2 constantvalue_index;
}
*/

public class ConstantValueAttribute implements ConstantInfo , AttributeInfo {

    public Uint16 constantValueIndex;


    @Override
    public void readInfo(ClassReader reader) {
        this.constantValueIndex = reader.readUint16();
    }



    public Uint16 ConstantValueIndex() {
        return this.constantValueIndex;
    }



    @Override
    public Object Value() {
        return null;
    }


    public Uint16 getConstantValueIndex() {
        return constantValueIndex;
    }

    public void setConstantValueIndex(Uint16 constantValueIndex) {
        this.constantValueIndex = constantValueIndex;
    }
}
