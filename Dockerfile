# Development environment for Minecraft plugin
FROM eclipse-temurin:21-jdk

# Install Maven and basic utilities
RUN apt-get update && apt-get install -y \
    maven \
    curl \
    git \
    && rm -rf /var/lib/apt/lists/*

# Create working directory
WORKDIR /app

# Copy project files
COPY . /app/

# Set up development environment
# Note: We do NOT build the plugin here - that's for the developer to do
ENV MAVEN_OPTS="-Xmx1024m"

# Default to bash shell for development
CMD ["/bin/bash"]