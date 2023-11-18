FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src
COPY ["LawAgency.Api/LawAgency.Api.csproj", "LawAgency.Api/"]
RUN dotnet restore "LawAgency.Api/LawAgency.Api.csproj"
COPY . .
WORKDIR "/src/LawAgency.Api"
RUN dotnet build "LawAgency.Api.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "LawAgency.Api.csproj" -c Release -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "LawAgency.Api.dll"]
