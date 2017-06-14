FROM solr:6.6-alpine

ENV CORE_DIR /opt/solr/server/solr

COPY ipni_view $CORE_DIR/ipni_view

USER root
RUN chown -R $SOLR_USER:$SOLR_USER $CORE_DIR
USER $SOLR_USER

