**Project requirements:**
<br />
* JDK9
* PostgreSQL (tested with version 9.0.1)
* Gradle
* Tomcat (or something else)

<br />
<br />

**Getting started:**

1. Clone repository `https://github.com/Shrralis/ss-blog-ee.git` or download ZIP.
1. Create database with PostgreSQL.
1. _(optional)_ Run database _dump.sql_ from _PROJECT_DIR/db/_ to restore test data
with next command: `psql -U postgres ss_blog < dump.sql`
1. Edit database properties like host, port, database name, database user and database password
at _PROJECT_DIR/src/main/java/com/shrralis/ssblog/config/DatabaseConfig_.
    * Default values are:
        - host: `localhost`,
        - port: `5432`,
        - database name: `ss_blog`
        - database username: `shrralis`,
        - database user password is _empty_)
1. Run `gradle build` if there is any error with tests then run `gradle build -x test` for building the project without
tests.
1. Deploy generated **WAR**-file from _PROJECT_DIR/build/libs/***.war_ into the directory where your **Tomcat**
is looking for **WAR** files.
1. Move the directory _PROJECT_DIR/uploaded_image_ into the same directory where you have moved the WAR-file.
1. Run **Tomcat**.