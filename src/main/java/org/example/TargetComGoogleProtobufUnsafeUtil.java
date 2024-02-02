package org.example;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

@TargetClass(className = "com.google.protobuf.UnsafeUtil")
public final class TargetComGoogleProtobufUnsafeUtil {
    @Substitute
    static sun.misc.Unsafe getUnsafe() {
        return null;
    }
}
