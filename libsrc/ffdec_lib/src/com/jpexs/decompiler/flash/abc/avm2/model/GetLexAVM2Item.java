/*
 *  Copyright (C) 2010-2022 JPEXS, All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.jpexs.decompiler.flash.abc.avm2.model;

import com.jpexs.decompiler.flash.abc.avm2.AVM2ConstantPool;
import com.jpexs.decompiler.flash.abc.types.Multiname;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.DottedChain;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.SimpleValue;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.Objects;

/**
 *
 * @author JPEXS
 */
public class GetLexAVM2Item extends AVM2Item implements SimpleValue {

    public Multiname propertyName;
    
    public GraphTargetItem type;
    
    public GraphTargetItem callType;
    
    public boolean isStatic;        

    private final DottedChain fullPropertyName;

    public GetLexAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, Multiname propertyName, AVM2ConstantPool constants, GraphTargetItem type, GraphTargetItem callType, boolean isStatic) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY);
        this.propertyName = propertyName;
        this.type = type;
        this.callType = callType;
        this.fullPropertyName = propertyName.getNameWithNamespace(constants, true);
        this.isStatic = isStatic;
    }

    public String getRawPropertyName() {
        return fullPropertyName.toRawString();
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) {
        String localName = propertyName.getNameWithCustomNamespace(localData.abc, localData.fullyQualifiedNames, false, true);
        getSrcData().localName = localName;
        return writer.append(propertyName.getNameWithCustomNamespace(localData.abc, localData.fullyQualifiedNames, false, true));
    }

    @Override
    public GraphTargetItem returnType() {
        return type;
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public boolean isSimpleValue() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.propertyName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GetLexAVM2Item other = (GetLexAVM2Item) obj;
        if (!Objects.equals(this.propertyName, other.propertyName)) {
            return false;
        }
        return true;
    }

}
