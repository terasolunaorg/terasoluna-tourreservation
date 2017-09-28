<host xmlns="http://caucho.com/ns/resin" xmlns:resin="urn:java:com.caucho.resin">

  <web-app id="/terasoluna-tourreservation-web" root-directory="webapps/terasoluna-tourreservation-web">
    <!-- settings of DataSource -->
    <database jndi-name="jdbc/tourreservationDataSource">
      <driver>
        <type>org.postgresql.Driver</type>
        <url>jdbc:postgresql://${HOST_IP!'localhost'}:${DBSRV_DB_PORT!'5432'}/tourreserve</url>
        <user>postgres</user>
        <password>P0stgres</password>
      </driver>
    </database>
    <!-- settings of class loader for web application -->
    <class-loader>
      <library-loader
        path="${VM_TOMCAT_ENV_JAR_DIR!'/opt/resin/resin/webapps-env-jars/terasoluna-tourreservation-env-resin-postgresql'}" />
    </class-loader>
  </web-app>

</host>
