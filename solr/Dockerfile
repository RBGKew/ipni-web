FROM solr:6.6-alpine

ENV CORE_DIR /opt/solr/server/solr

USER root

COPY start.sh /opt/docker-solr/scripts
COPY ipni $CORE_DIR/ipni_1
COPY ipni $CORE_DIR/ipni_2

RUN chown -R $SOLR_USER:$SOLR_USER $CORE_DIR \
  && chown $SOLR_USER:$SOLR_USER /opt/docker-solr/scripts \
  && chmod 775 /opt/docker-solr/scripts \
  && /opt/docker-solr/scripts/start.sh bootstrap

USER $SOLR_USER

CMD ["start.sh"]
