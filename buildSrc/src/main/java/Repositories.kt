/*
 *    Copyright 2020 Criteo
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.jfrog.bintray.gradle.BintrayExtension
import io.github.gradlenexus.publishplugin.NexusPublishExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.initialization.dsl.ScriptHandler
import org.gradle.kotlin.dsl.the

fun Project.addDefaultInputRepository() {
  repositories.addDefaultInputRepository()
}

fun ScriptHandler.addDefaultInputRepository() {
  repositories.addDefaultInputRepository()
}

internal fun RepositoryHandler.addDefaultInputRepository() {
  google()
  mavenCentral()
  jcenter {
    content {
      includeGroup("com.mopub")
      includeGroup("com.criteo.mediation.mopub")
      includeGroup("com.criteo.mediation.google")
      includeModule("org.jetbrains.trove4j", "trove4j") // https://youtrack.jetbrains.com/issue/IDEA-261387#focus=Comments-27-4726891.0-0
      includeGroup("org.jetbrains.kotlinx") // https://github.com/Kotlin/kotlinx.html/issues/173
    }
  }
  maven {
    setUrl("https://jitpack.io")
  }
}

/**
 * Add a local repository in the build directory for development and testing purpose.
 * Compared to the maven local repository, this has the advantage to be cleaned by cleaning tasks
 * and has no side effect on other working directory.
 */
internal fun Project.addDevRepository() {
  val devRepository = "${project.buildDir}/dev-${project.sdkPublicationVersion()}"

  publishing {
    repositories {
      maven {
        name = "Dev"
        setUrl("file://$devRepository")
      }
    }
  }
}

fun Project.addSonatypeOutputRepository() {
  the<NexusPublishExtension>().apply {
    repositories {
      sonatype {
        username.set("criteo-oss")
        password.set(System.getenv("SONATYPE_PASSWORD"))
      }
    }
  }
}

fun Project.addBintrayRepository(configure: BintrayExtension.() -> Unit = {}) {
  the<BintrayExtension>().apply {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    publish = true

    with(pkg) {
      repo = "mobile"
      userOrg = "criteo"
      name = "publisher-sdk"
      desc = Publications.sdkDescription
      websiteUrl = Publications.website
      vcsUrl = Publications.githubUrl
      setLicenses("Apache-2.0")
      publicDownloadNumbers = true

      with(version) {
        name = sdkPublicationVersion()
      }
    }

    afterEvaluate {
      val publicationNames = publishing.publications.map { it.name }.toTypedArray()
      setPublications(*publicationNames)
    }

    configure(this)
  }
}
