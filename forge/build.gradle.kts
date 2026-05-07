plugins {
    id("com.possible-triangle.forge")
}

forge {
    enableMixins()

    dependOn(project(":common"))
}

upload.modrinth {
    syncBodyFromReadme()
}
