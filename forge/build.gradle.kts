import net.minecraftforge.gradle.userdev.jarjar.JarJarProjectExtension

val mixin_extras_version: String by extra

forge {
    enableMixins()

    dependOn(project(":common"))
}

val jarJar = the<JarJarProjectExtension>()

dependencies {
    compileOnly(annotationProcessor("io.github.llamalad7:mixinextras-common:${mixin_extras_version}")!!)
    implementation("jarJar"("io.github.llamalad7:mixinextras-forge:${mixin_extras_version}")) {
        jarJar.ranged(this, "[${mixin_extras_version},)")
    }
}

uploadToCurseforge()
uploadToModrinth {
    syncBodyFromReadme()
}