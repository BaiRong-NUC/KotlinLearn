#!/usr/bin/env bash
set -euo pipefail

source_file="$1"
name="${source_file%.kt}"
jar_dir="/d/Android/Learn/.jar"
coroutines="D:/Kotlin/kotlinc/lib/kotlinx-coroutines-core-jvm.jar"

mkdir -p "$jar_dir"
/d/Kotlin/kotlinc/bin/kotlinc.bat "$source_file" -classpath "$coroutines" -include-runtime -d "$jar_dir/$name.jar"

package_name=$(sed -n $'1s/^\xEF\xBB\xBF//; s/^[[:space:]]*package[[:space:]]\\+\\([^;[:space:]]\\+\\).*/\\1/p' "$source_file" | head -n 1 | tr -d '`')
main_class="${name^}Kt"
if [[ -n "$package_name" ]]; then
    main_class="$package_name.$main_class"
fi
java -cp "D:/Android/Learn/.jar/$name.jar;$coroutines" "$main_class"
