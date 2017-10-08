/*
 * Copyright © 2017 Michael Weirauch (michael.weirauch@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.mweirauch.micrometer.jvm.extras.procfs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProcfsStatus extends ProcfsEntry {

    public enum KEY implements ValueKey {
        /**
         * Threads
         */
        THREADS
    }

    public ProcfsStatus() {
        super(ProcfsReader.getInstance("status"));
    }

    /* default */ ProcfsStatus(ProcfsReader reader) {
        super(reader);
    }

    @Override
    protected Map<ValueKey, Double> handle(Collection<String> lines) {
        Objects.requireNonNull(lines);

        final Map<ValueKey, Double> values = new HashMap<>();

        for (final String line : lines) {
            if (line.startsWith("Threads:")) {
                values.put(KEY.THREADS, parseValue(line));
            }
        }

        return values;
    }

    private static Double parseValue(String line) {
        Objects.requireNonNull(line);

        return Double.parseDouble(line.split("\\s+")[1]);
    }

}
