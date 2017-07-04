FROM solr:6.6-alpine

ENV CORE_DIR /opt/solr/server/solr

USER root
COPY ipni $CORE_DIR/ipni_1
COPY ipni $CORE_DIR/ipni_2
RUN /bin/echo "name=ipni_1" >> $CORE_DIR/ipni_1/core.properties; \
	 /bin/echo "name=ipni_2" >> $CORE_DIR/ipni_2/core.properties;


RUN chown -R $SOLR_USER:$SOLR_USER $CORE_DIR
USER $SOLR_USER

